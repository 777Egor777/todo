<%@ page import="ru.job4j.todo.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <title>ToDo list</title>
    <script>
        function validate() {
            if ($('#desc').val() === '') {
                alert("Текст новой задачи пуст!")
                return false;
            }
            return true;
        }
        $(document).ready(
            function () {
                renderTable();
            }
        );
        function renderTable() {
            $.ajax({
                type: 'GET',
                url: '<%=request.getContextPath()%>/index.do?show_all=' + $('#show_all').is(":checked"),
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    console.log(data);
                    let tasks = data.tasks;
                    let content = "";
                    for (let i = 0; i < tasks.length; i++) {
                        content += '<tr><td>' + tasks[i].desc + '</td><td>';
                        if (tasks[i].done === true) {
                            content += "<label><input type=\"checkbox\" checked=\"checked\" disabled></label>"
                        } else {
                            let id = tasks[i].id;
                            let onclickStr = "\"return closeTask(" + id + ");\"";
                            content += "<label><input type=\"checkbox\" onclick=" + onclickStr + "></label>";
                        }
                        content += "</td></tr>";
                    }
                    $('#table_body').html(content);
                }
            })
        }
        function closeTask(id) {
            let result = false;
            $.ajax({
                type: 'POST',
                url: '<%=request.getContextPath()%>/index.do',
                data: {"task_id": id},
                success: function() {
                    renderTable();
                    result =  true;
                }
            });
            return result;
        }
    </script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">ToDo</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <% Object objUser = request.getSession().getAttribute("user"); %>
                    <% if (objUser == null) { %>
                    <a class="nav-link" href="<%=request.getContextPath()%>/auth.do">Войти в систему</a>
                    <% } else { %>
                    <a class="nav-link" href="<%=request.getContextPath()%>/auth.do">
                        <%=((User) objUser).getName()%> | Выйти
                    </a>
                    <% } %>
                </li>
                <li>
                    <a class="nav-link" href="<%=request.getContextPath()%>/reg.do">Регистрация</a>
                </li>
            </ul>
            <div class="form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="checkbox" value="all" id="show_all" name="show_all" checked="checked"
                    onclick='renderTable()'>Показать всё
                </label>
            </div>
        </div>
    </div>
</nav>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Добавить задачу
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/index.do" method="post">
                    <div class="form-group">
                        <label>Описание</label>
                        <textarea rows="10" cols="45" name="desc" id="desc" placeholder="Введите задачу"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Добавить</button>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Задачи
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Задача</th>
                        <th scope="col">Выполнено</th>
                    </tr>
                    </thead>
                    <tbody id="table_body">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
