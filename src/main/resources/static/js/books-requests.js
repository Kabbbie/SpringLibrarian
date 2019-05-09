
$(function(){

    var page=1
    var ascSort=true;
    var sortParam="author";

    var authorCol="<th id='author-header' class='sorted'>";
    var titleCol="<th id='title-header'>";
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

    $("#submit-add-button").on("click",function(){
        	var formData={
                isbn: $("#isbn").val(),
            	 author: $("#author").val(),
            	 title: $("#title").val(),
            	 username: null
            }
            console.log(formData);

            $.ajax({
            	url:"api/saveBook",
            	type:"POST",
            	headers:{
            	    "Content-Type":"application/json",
            	    "Accept":"application/json"
            	},
            	data : JSON.stringify(formData),
            	dataType : 'json',
            	success: function(result){
            		if(result.status=="success-create"){
            		    fetchData();
            			alert("Book has been added");
                       console.log("Книга успешно добавлена",result);
            		}
            		if(result.status=="error-create"){
            		    fetchData();
            	        alert("Книга с таким ISBN уже существует!");
            		}
            	},
            	error: function(result){
            		alert("Книга с таким ISBN уже существует");
            	}
            });
    });
    $("#submit-update-button").on("click",function(){
                      	var formData={
                              isbn: $("#isbn").val(),
                              author: $("#author").val(),
                              title: $("#title").val(),
                              username: null
                          }
                          console.log(formData);

                          $.ajax({
                          	url:"api/updateBook",
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
                          	        alert("Книга был успешно изменён");
                                     console.log(result);
                          		}
                          		if(result.status=="error"){
                          		    fetchData();
                          	        alert("Такой книги не существует");
                          		}
                          	}
                          });
                  });
    function setBtn(user,currentUser){
    var takeButton="<button type='button' class='take-button'> Взять </button>";
    var returnButton="<button type='button' class='return-button'> Вернуть </button>";
        if(user==null){
            return takeButton;
        }
        else{
            if(user==currentUser){
                return returnButton;
            }
            if(user!=currentUser){
                return user;
            }
        }
    }

    $(document).on('click','.take-button',function(event){
        var currentRow=$(this).closest('tr').index();
        // Получаем строку из таблицы
        var cR=$(this).closest('tr');
        // Получаем значения ячеек
        var isbnReq=cR.find('.isbn-text').text()
        var temp=isbnReq.toString();
        console.log("Current: "+isbnReq);
        $.ajax({
            url:"api/giveBookToUser",
            type:"PUT",
            data:{
                isbn: temp
            },
            success: function(){
                fetchData();
            }
        });

    });

    $(document).on('click','.return-button',function(event){
            var currentRow=$(this).closest('tr').index();
            // Получаем строку из таблицы
            var cR=$(this).closest('tr');
            // Получаем значения ячеек
            var isbnReq=cR.find('.isbn-text').text()
            var temp=isbnReq.toString();
            console.log("Current: "+isbnReq);
            $.ajax({
                url:"api/returnBookFromUser",
                type:"PUT",
                data:{
                    isbn: temp
                },
                success: function(){
                    fetchData();
                }
            });

    });

    $(document).on('click','.isbn-text',function(event){

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
                var isbnReq=cR.find('.isbn-text').text()
                var temp=isbnReq.toString();
                $("#isbn").val(temp);
                $("#cr-up-header").text("Изменить книгу");
                modal.style.display = "block";

                span.onclick = function() {
                    modal.style.display = "none";
                }
                console.log("Current: "+isbnReq);


    });

    $(document).on('click','.delete-button',function(event){
                var currentRow=$(this).closest('tr').index();
                // Получаем строку из таблицы
                var cR=$(this).closest('tr');
                // Получаем значения ячеек
                var isbnReq=cR.find('.isbn-text').text()
                var temp=isbnReq.toString();
                console.log("Current: "+isbnReq);
                var deleteConfirm=confirm("Удалить книгу?");
                if(deleteConfirm){
                    $.ajax({
                        url:"api/deleteBook",
                        type:"DELETE",
                        data:{
                            isbn: temp
                        },
                        success: function(result){
                            if(result.status=="success"){
                                alert("Книга успешно удалена");
                                fetchData();
                            }
                            if(result.status=="error"){
                                alert("Нельзя удалить книгу,так как она взята пользователем");
                                fetchData();
                            }
                            if(result.status=="deleteError"){
                                alert("Произошла ошибка! Книга не удалена");
                                fetchData();
                            }
                        }
                    });
                }

        });

    $(document).on('click','#author-header',function(event){
        if(sortParam=="author"){
            ascSort=(!ascSort);
            fetchData();
            document.getElementById("author-header").className = "sorted";
            console.log("au1 "+sortParam+","+ascSort);
        }else{
            ascSort=true;
            sortParam="author";
            authorCol="<th class='sorted' id='author-header'>";
            titleCol="<th id='title-header'>";
            fetchData();
            document.getElementById("author-header").className = "sorted";
            console.log("au2 "+sortParam+","+ascSort);
        }
    });

    $(document).on('click','#title-header',function(event){
        if(sortParam=="title"){
            ascSort=(!ascSort);

            fetchData();
            console.log("ti1 "+sortParam+","+ascSort);
        }else{
            ascSort=true;
            sortParam="title";
            authorCol="<th id='author-header'>";
            titleCol="<th class='sorted' id='title-header'>";
            fetchData();
            console.log("ti2 "+sortParam+","+ascSort);
        }
    });

    function fetchData(){
    	$.ajax({
		    url: "api/getBooksPage",
		    type: "GET",
		    data:{
	            page: page,
	            ascSort: ascSort,
	            sortParam: sortParam
		    },
		    success: function(result){
		        console.log("I called with page:",page)
		        if(result.status=="success"){
		            console.log("Success: ", result);
		        }
		        else{
		            console.log("Fail: ", result);
		        }
			    var datArr=result.data.page;
			    totalRecords=result.data.totalRecords;
			    console.log(totalRecords);
			    var currentUser=result.user;

                var html="<div class='container'><table class='table'><tr><th>ISBN</th>";

                html+=authorCol+"Автор</th>";
                html+=titleCol+"Название</th>";

                html+="<th>Владелец</th><th>Удалить</th></tr>";
                var deleteButton="<button type='button' class='delete-button'> Удалить </button>";
                for(var i=0;i<datArr.length;i++){
                    html+="<tr>";
                    html+="<td class='isbn-text'>"+datArr[i].isbn+"</td>";
                    html+="<td class='author-text'>"+datArr[i].author+"</td>";
                    html+="<td class='title-text'>"+datArr[i].title+"</td>";
                    html+="<td>"+setBtn(datArr[i].username,currentUser)+"</td>";
                    html+="<td>"+deleteButton+"</td>";
                    html+="</tr>";
                }
                html+="</table></div>";
                $("#result").html(html);
	        }
	    });
    }

})