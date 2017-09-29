/*Função para que faz aparecer botão de voltar ao topo*/
 $(document).ready(function() {
    var offset = 220;
    var duration = 500;
    $(window).scroll(function() {
        if ($(this).scrollTop() > offset) {
            $('#scrollUp').fadeIn(duration);
        } else {
            $('#scrollUp').fadeOut(duration);
        }
    });
    
    $('#scrollUp').click(function(event) {
        event.preventDefault();
        $('html, body').animate({scrollTop: 0}, duration);
        return false;
    })
});
