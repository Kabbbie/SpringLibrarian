$(function(){
    var page=1;
    fetchData();

     $("#prevPage").on("click",function(){
        	if(page>1){
        		page--;
        		console.log("Page:",page);
        		fetchData();
        	}
     });

     $("#nextPage").on("click",function(){
        	if(page*5<totalRecords){
        		page++;

        		console.log("Page:",page);
        		fetchData();
        	}
     });

     $(document).on('click','.delete-button',function(event){
         var currentRow=$(this).closest('tr').index();
                     // Получаем строку из таблицы
         var cR=$(this).closest('tr');
                     // Получаем значения ячеек
         var usernameReq=cR.find('.username-text').text()
         var temp=usernameReq.toString();
         console.log("Current: "+usernameReq);
         var deleteConfirm=confirm("Удалить пользователя?");
         if(deleteConfirm){
            $.ajax({
                url:"api/deleteUser",
                type:"DELETE",
                data:{
                    username: temp
                },
                success: function(result){
                    if(result.status=="success"){
                        alert("Пользователь успешно удалена");
                            fetchData();
                        }
                        if(result.status=="current-user-error"){
                            alert("Нельзя удалить самого себя");
                            fetchData();
                        }
                        if(result.status=="has-book-error"){
                            alert("На пользователя записаны книги, удаление невозможно!");
                            fetchData();
                        }
                }
             });
         }

     });

     $(document).on('click','.username-text',function(event){

                     var modal = document.getElementById('addWindow');
                     var span = document.getElementsByClassName("close")[0];
                     var updateButton=document.getElementById("submit-update-button");
                     var addButton=document.getElementById("submit-add-button");

                     updateButton.style.display="block";
                     addButton.style.display="none";

                     var currentRow=$(this).closest('tr').index();
                     // Получаем строку из таблицы
                     var cR=$(this).closest('tr');
                     // Получаем значения ячеек
                     var usernameReq=cR.find('.username-text').text()
                     var temp=usernameReq.toString();
                     $("#username").val(temp);
                     $("#cr-up-header").text("Изменить пользователя");
                     modal.style.display = "block";

                     span.onclick = function() {
                         modal.style.display = "none";
                     }
                     console.log("Current: "+usernameReq);


         });

     $("#submit-add-button").on("click",function(){
             	var formData={
                     username: $("#username").val(),
                 	 password: $("#password").val()
                 }
                 console.log(formData);

                 $.ajax({
                 	url:"api/createUser",
                 	type:"POST",
                 	headers:{
                 	    "Content-Type":"application/json",
                 	    "Accept":"application/json"
                 	},
                 	data : JSON.stringify(formData),
                 	dataType : 'json',
                 	success: function(result){
                 		if(result.status=="success"){
                 		    fetchData();
                 	        alert("Пользователь был успешно добавлен");
                            console.log("Пользователь успешно добавлен",result);
                 		}
                 		if(result.status=="error"){
                 		    fetchData();
                 	        alert("Такой пользователь уже существует");
                 		}
                 	}
                 });
         });
     $("#submit-update-button").on("click",function(){
                      	var formData={
                              username: $("#username").val(),
                          	 password: $("#password").val()
                          }
                          console.log(formData);

                          $.ajax({
                          	url:"api/updateUser",
                          	type:"PUT",
                          	headers:{
                          	    "Content-Type":"application/json",
                          	    "Accept":"application/json"
                          	},
                          	data : JSON.stringify(formData),
                          	dataType : 'json',
                          	success: function(result){
                          		if(result.status=="success"){
                          		    fetchData();
                          	        alert("Пользователь был успешно изменён");
                                     console.log("Пользователь успешно добавлен",result);
                          		}
                          		if(result.status=="error"){
                          		    fetchData();
                          	        alert("Такого пользователя не существует");
                          		}
                          	}
                          });
                  });

    function fetchData(){
        	$.ajax({
    		    url: "api/getUsersPage",
    		    type: "GET",
    		    data:{
    	            page: page
    		    },
    		    success: function(result){
    		        console.log("I called with page:",page);

    			    var datArr=result.data.page;
    			    totalRecords=result.data.totalRecords;

    			    console.log(totalRecords);

    			    var currentUser=result.user;

                    var html="<div class='container'><table class='table'><tr><th>Имя пользователя</th>";
                    html+="<th>Удалить</th></tr>";
                    var deleteButton="<button type='button' class='delete-button'> Удалить </button>";
                    for(var i=0;i<datArr.length;i++){
                        html+="<tr>";
                        html+="<td class='username-text'>"+datArr[i].username+"</td>";
                        html+="<td>"+deleteButton+"</td>";
                        html+="</tr>";
                    }
                    html+="</table></div>";
                    $("#result").html(html);
    	        }
    	    });
    }
})