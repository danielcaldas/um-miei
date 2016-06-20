// Função para alterar comportamento do avatar.
//0 - Estático
//1 - Erro
//2 - Falar
//3 - Confuso
//tempo - valor em milisegundos que queremos que demore a acção
function mudarAvatar(display,tempo) {

    setTimeout(
        function () {
            $("#avatar").attr("src", "/App_Themes/static.png");
        }, tempo);

    if (display == 0) {
        $("#avatar").attr("src", "/App_Themes/static.png");
    } else if (display == 1) {
        $("#avatar").attr("src", "/App_Themes/erro.gif");
    } else if (display == 2) {
        $("#avatar").attr("src", "/App_Themes/speak.gif");
    } else if (display == 3) {
        $("#avatar").attr("src", "/App_Themes/confundus.gif");
    }
}


function onClickAvatar() {
    mudarAvatar(display, 6000);
    falar();
}

function falar() {
    $.ajax({
        url: '/Home/Falar',
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify('@string.Empty'),
        success: function (data) {
            debugger;
        },
        error: function () {
            debugger;
        },
    });
}