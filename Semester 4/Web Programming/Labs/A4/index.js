function game() {
    const title = document.getElementById("title");
    const fields = document.getElementsByClassName('field');
    const gridArray = Array.from(fields);
    var tracking = [1, 2, 3, 4, 5, 6, 7, 8, 9];

    Array.from(fields).forEach((field) => {
        field.addEventListener("click", (e) => {
            const index = gridArray.indexOf(e.target);

            if (fields[index].classList.contains("X") || fields[index].classList.contains("O")) {
                return;
            }

            fields[index].classList.add("X");
            const spliceNumber = tracking.indexOf(index + 1);
            tracking.splice(spliceNumber, 1);

            if (checkWin("X", fields)) {
                title.innerHTML = "Player X wins!";
                document.body.classList.add("over");
                return;
            }

            if (tracking.length === 0) {
                title.innerHTML = "It's a draw!";
                document.body.classList.add("over");
                return;
            }

            const randomIndex = Math.floor(Math.random() * tracking.length);
            const CPUIndex = tracking[randomIndex];
            fields[CPUIndex - 1].classList.add("O");
            const spliceNumberCPU = tracking.indexOf(CPUIndex);
            tracking.splice(spliceNumberCPU, 1);

            if (checkWin("O", fields)) {
                title.innerHTML = "Player O wins!";
                document.body.classList.add("over");
                return;
            }
        })
    });
}

function checkWin(player, fields) {
    function check(a, b, c) {
        if (fields[a].classList.contains(player) & fields[b].classList.contains(player) & fields[c].classList.contains(player))
            return true;
        else
            return false;
    }

    if (check(0, 3, 6))
        return true;
    else if (check(1, 4, 7))
        return true;
    else if (check(2, 5, 8))
        return true;
    else if (check(0, 1, 2))
        return true;
    else if (check(3, 4, 5))
        return true;
    else if (check(6, 7, 8))
        return true;
    else if (check(0, 4, 8))
        return true;
    else if (check(2, 4, 6))
        return true;
    else
        return false;
}

game();