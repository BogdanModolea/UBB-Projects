$(document).ready(function () {

    var pictureWidth = 300;
    var position = 0;
    var popUpDiv = document.getElementById("popUpDiv");
    var popUpImg = document.getElementById("popUpImg");

    // Setting the position for each image
    $("li").each(function () {
        position += pictureWidth;
        $(this).css("left", position);
    });

    $("img").click(function () {
        var img = $(this).attr('src');
        popUpDiv.style.display = 'block';
        popUpImg.src = img;
        $("li").stop(true);
    });

    function slide() {
        $("li").animate({ "left": "+=10px" }, 50, again);
    }

    function again() {
        var left = $(this).parent().offset().left + $(this).offset().left;
        if (left >= 1920) {
            $(this).css("left", left - 1920);
        }
        slide();
    }

    popUpImg.onclick = function () {
        popUpDiv.style.display = 'none';
        slide();
    }

    slide();
});