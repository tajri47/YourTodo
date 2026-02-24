document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("registerForm");

    const usernameInput = document.getElementById("username");
    const usernameStatus = document.getElementById("usernameStatus");

    const passwordInput = document.getElementById("password");
    const confirmInput = document.getElementById("confirmPassword");

    const passwordRules = document.getElementById("passwordRules");
    const matchStatus = document.getElementById("matchStatus");

    const submitBtn = document.getElementById("submitBtn");


    let usernameValid = false;
    let passwordValid = false;
    let matchValid = false;



    usernameInput.addEventListener("input", function () {

        const username = usernameInput.value.trim();

        usernameValid = false;
        updateSubmit();

        if (username.length < 3) {

            usernameStatus.classList.add("hidden");
            return;

        }

        fetch("/check-username?username=" + encodeURIComponent(username))

            .then(r => r.text())

            .then(data => {

                data = data.trim();

                usernameStatus.classList.remove("hidden");

                if (data === "TAKEN") {

                    usernameStatus.innerText =
                        "Username already taken";

                    usernameStatus.className =
                        "helper-text invalid";

                    usernameValid = false;

                } else {

                    usernameStatus.innerText =
                        "Username available";

                    usernameStatus.className =
                        "helper-text valid";

                    usernameValid = true;

                }

                updateSubmit();

            })

            .catch(() => {

                usernameValid = false;
                updateSubmit();

            });

    });



    passwordInput.addEventListener("input", function () {

        const p = passwordInput.value;

        passwordValid = false;
        updateSubmit();

        if (p.length === 0) {

            passwordRules.classList.add("hidden");
            return;

        }

        passwordRules.classList.remove("hidden");

        const length = p.length >= 8;
        const letter = /[A-Za-z]/.test(p);
        const number = /[0-9]/.test(p);
        const special = /[^A-Za-z0-9]/.test(p);

        document.getElementById("rule-length").innerText =
            (length ? "✓ " : "• ") + "8 characters";

        document.getElementById("rule-letter").innerText =
            (letter ? "✓ " : "• ") + "Letter";

        document.getElementById("rule-number").innerText =
            (number ? "✓ " : "• ") + "Number";

        document.getElementById("rule-special").innerText =
            (special ? "✓ " : "• ") + "Special character";

        passwordValid =
            length && letter && number && special;

        checkMatch();
        updateSubmit();

    });



    confirmInput.addEventListener("input", function () {

        checkMatch();
        updateSubmit();

    });



    function checkMatch() {

        const p = passwordInput.value;
        const c = confirmInput.value;

        matchValid = false;

        if (c.length === 0) {

            matchStatus.classList.add("hidden");
            return;

        }

        matchStatus.classList.remove("hidden");

        if (p === c && passwordValid) {

            matchStatus.innerText =
                "Passwords match";

            matchStatus.className =
                "helper-text valid";

            matchValid = true;

        } else {

            matchStatus.innerText =
                "Passwords do not match";

            matchStatus.className =
                "helper-text invalid";

            matchValid = false;

        }

    }



    function updateSubmit() {

        submitBtn.disabled =
            !(usernameValid &&
              passwordValid &&
              matchValid);

    }



    form.addEventListener("submit", function (e) {

        if (submitBtn.disabled) {

            e.preventDefault();

        }

    });

});