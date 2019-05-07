$(function(){

    var page=1
    fetchData();
    $("#prevPage").on("click",function(){
    	if(page>1){
    		page--;
    		console.log("Page:",page);
    		fetchData();
    	}
    });

    $("#nextPage").on("click",function(){
    	if(page<3){
    		page++;

    		console.log("Page:",page);
    		fetchData();
    	}
    });
    function setBtn(user){
    var getButton="<button type=\"button\" class=\"btn btn-primary\">Take</button>";
        if(user==null){
            return getButton;
        }
        else{
            return user;
        }
    }

    function fetchData(){
    	$.ajax({
		url: "/temp",
		type: "GET",
		data:{
	    page: page
		},
		success: function(result){
		console.log("I called with page:",page)
		    if(result.status=="success"){
		        console.log("Success: ", result);
		    }
		    else{
		        console.log("Fail: ", result);
		    }
			    var datArr=result.data;

                var html="<div class='table-row'><table><tr><th>ISBN</th><th>Author</th><th>Title</th><th>Owner</th></tr>";
                for(var i=0;i<datArr.length;i++){
                    html+="<tr>"+
                    			"<td>"+datArr[i].isbn+"</td>"+
                    		    "<td>"+datArr[i].author+"</td>"+
                    		    "<td>"+datArr[i].title+"</td>"+
                    		    "<td>"+setBtn(datArr[i].username)+"</td>"+
                    	  "</tr>";
                }
                html+="</table></div>";
                $("#result").html(html);
	    }
	});
    }

})