function toggleTask(id) {

    window.location = "/toggle/" + id;

}


function deleteTask(id) {

    if (confirm("Delete task?")) {

        window.location = "/delete/" + id;

    }

}


function toggleSidebar()
{
    document.querySelector(".wrapper").classList.toggle("collapsed");

    document.getElementById("sidebar").classList.toggle("collapsed");
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