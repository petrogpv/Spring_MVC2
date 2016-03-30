<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
<h4><a href="/">Main page</a></h4><br>
<input type="submit" form = "myform" value="Delete checked photos" />
<div class="container">


    <table class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>Photo</b></td>
            <td><b>id</b></td>
        </tr>
        </thead>
        <form id = "myform" action="/delete" method="post">
        <c:forEach items="${photos}" var="photo">
            <tr>
                <td><input type="checkbox" name="toDelete[]" value="${photo.id}"/></td>
                <td><a href=/photo/${photo.id}><img hight = "50" width="50" src="/photo/${photo.id}"></a></td>
                <td>${photo.id}</td>
            </tr>
        </c:forEach>
        </form>
    </table>
</div>

<script>

    $('#delete_contact').click(function(){
        var data = { 'toDelete[]' : []};
        $(":checked").each(function() {
            data['toDelete[]'].push($(this).val());
        });
        $.post("/delete", data);
    })

</script>
</body>
</html>
