function ajaxPost() {
	    	var formData={
	    		isbn: $("#isbn").val(),
	    		author: $("#author").val(),
	    		title: $("#title").val(),
	    		username: null
	    	}
	    	console.log(formData)

			$.ajax({
				url:"addBook",
				type:"POST",
				headers:{
				    "Content-Type":"application/json",
				    "Accept":"application/json"
				},
				data : JSON.stringify(formData),
				dataType : 'json',
				success: function(result){
					console.log("Книга успешно добавлена",result);
				}


			});
		}
