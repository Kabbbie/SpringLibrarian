window.onload=function(){
    var modal = document.getElementById('addWindow');
    var btn = document.getElementById("add-book-button");
    var updateButton=document.getElementById("submit-update-button");
    var addButton=document.getElementById("submit-add-button");
    var span = document.getElementsByClassName("close")[0];

    btn.onclick = function() {
        modal.style.display = "block";
        updateButton.style.display="none";
        addButton.style.display="block";
        $("#cr-up-header").text("Добавить пользователя");
    }
    span.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}