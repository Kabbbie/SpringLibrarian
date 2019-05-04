$(function doAjax (){
    $.ajax({
        url: "http://localhost:8080/book",
        type: "GET",
        success: function(data){
            console.log(data);
            var datArr=data.success.data;
            var html="";
            for(var i=0;i<datArr.length,i++){
                html+="<div class='sample-user>"+
                    "<h3>"+datArr[i].isbn+"/h3"
                +"</div>"
            }
            $("#result").html(html);
        }
    });
});