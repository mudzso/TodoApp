/**
 * Created by mudzso on 2017.05.11..
 */
$(document).ready(function () {
   getTodos();
   var $sendButton = $('#btn');
   var $text = $('#Text');
   var $backlog = $('#backlog');
   var $active = $('#active');
   var $done = $('#done');

   $sendButton.on('click', function(){
       sendTodo(getTodos);
   });

   function sendTodo ( callback ) {
       var text = $text.val();
       var data = 'Text=' + text + '&Status=BACKLOG';
       $.ajax({
           type: 'PUT',
           url: './TodoServlet?' + data,
           success: callback
       });
   }

    function getTodos() {
       setTimeout(function () {
           $.ajax({
               type: 'GET',
               url: './TodoServlet',
               success: render
           });
       }, 0);
    }

    function deleteTodo(id){
       $.ajax({
           type : "DELETE",
           url : "./TodoServlet?TodoID="+id,
           success: getTodos
       })
    }
    function switchStatus(id,newStatus) {
        $.ajax({
            type : "POST",
            url : "./TodoServlet",
            data : {
                TodoID: id,
                Status : newStatus,
                success : getTodos
            }
        });
    }

    function render (data) {
       $backlog.empty();
       $active.empty();
       $done.empty();
        for (var i = 0; i< data.length; i++){
            var currentElement = data[i];
            var todo = createTodo(currentElement.text, currentElement.status, currentElement.id);

            if(currentElement.status === "BACKLOG"){
                $backlog.append(todo);
            }else if(currentElement.status === "ACTIVE"){
                $active.append(todo);
            }else if(currentElement.status === "DONE"){
                $done.append(todo);
            }
        }
    }

    function createTodo(text,status,id) {
       var buttonText = '';
       var newStatus = '';
       if(status ==="BACKLOG"){
           buttonText = "activate";
           newStatus = 'ACTIVE';
       } else if(status === "ACTIVE") {
           buttonText = 'done';
           newStatus = 'DONE';
       } else if (status === 'DONE') {
           buttonText = 'undone';
           newStatus = 'ACTIVE';
       }

       var $todo = $('<li>', {id: id})
            .append($('<span>').text(text))
            .append($('<button>', {newstatus: newStatus,class:"firstButton"})
                .text(buttonText)
                .on('click', switchStatus.bind(null, id, newStatus) ))
            .append($('<button>',{class:"secondButton"})
                .text('delete')
                .on('click', deleteTodo.bind(null,id)));

       return $todo;
    }

});

