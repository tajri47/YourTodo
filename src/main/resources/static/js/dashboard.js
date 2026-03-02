// SAVE SCROLL POSITION BEFORE PAGE RELOAD
window.addEventListener("beforeunload", function ()
{
    localStorage.setItem("scrollPosition", window.scrollY);
});

function toggleTask(id)
{
    fetch("/toggle/" + id)
    .then(() => location.reload());
}


function deleteTask(id)
{
    if(confirm("Delete task?"))
    {
        fetch("/delete/" + id)
        .then(() => location.reload());
    }
}


function toggleSidebar()
{
    const html = document.documentElement;
    const wrapper = document.querySelector(".wrapper");
    const sidebar = document.getElementById("sidebar");

    html.classList.toggle("sidebar-collapsed");
    wrapper.classList.toggle("collapsed");
    sidebar.classList.toggle("collapsed");

    // save state
    if(html.classList.contains("sidebar-collapsed"))
    {
        localStorage.setItem("sidebarCollapsed", "true");
    }
    else
    {
        localStorage.setItem("sidebarCollapsed", "false");
    }
}


document.getElementById("search")

    .addEventListener("keyup", function () {

        let value = this.value.toLowerCase();


        document.querySelectorAll(".task-row")

            .forEach(card => {

                card.style.display =

                    card.innerText.toLowerCase()

                        .includes(value)

                        ? "flex"

                        : "none";

            });

    });

document.addEventListener("DOMContentLoaded", function()
{
    let today = new Date();

    let minDate = today.toISOString().split("T")[0];

    let due = document.getElementById("dueDate");

    if(due)
        due.setAttribute("min", minDate);
});

document.addEventListener("DOMContentLoaded", function()
{
    // RESTORE SIDEBAR
    const collapsed = localStorage.getItem("sidebarCollapsed");

    if(collapsed === "true")
    {
        document.querySelector(".wrapper").classList.add("collapsed");
        document.getElementById("sidebar").classList.add("collapsed");
    }


    // RESTORE SCROLL POSITION
    const scrollPosition = localStorage.getItem("scrollPosition");

    if(scrollPosition !== null)
    {
        window.scrollTo(0, parseInt(scrollPosition));
    }
});

//DARK MODE TOGGLE

function toggleDarkMode()
{
    document.body.classList.toggle("dark-mode");

    localStorage.setItem(
        "darkMode",
        document.body.classList.contains("dark-mode")
    );
}


//LOAD SAVED MODE

document.addEventListener("DOMContentLoaded", function()
{
    if(localStorage.getItem("darkMode") === "true")
    {
        document.body.classList.add("dark-mode");
    }
});