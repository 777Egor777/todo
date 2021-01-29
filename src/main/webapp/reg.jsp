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
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>ToDo. Регистрация.</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-carBrand" href="#">ToDo</a>
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
        </div>
    </div>
</nav>
<script>
    $(document).ready(
        function() {
            let errMsg = <%=request.getParameter("errMsg")%>
            if (errMsg != null) {
                alert(errMsg);
            }
        }
    );
    function validate() {
        let result = true;
        let login = $('#login').val();
        let password = $('#pwd').val();
        let name = $('#name').val();
        console.log(name);
        console.log(password);
        console.log(login);
        if (login == '') {
            alert('Login field is empty');
            result = false;
        } else if (password == '') {
            alert('Password field is empty');
            result = false;
        } else if (name == '') {
            alert('Name field is empty');
            result = false;
        }
        return result;
    }
</script>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Регистрация
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg.do" method="post" accept-charset="x-UTF-16LE-BOM">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" class="form-control" name="name" id="name" placeholder="Имя">
                    </div>
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input type="text" class="form-control" name="login" id="login" placeholder="Логин">
                    </div>
                    <div class="form-group">
                        <label for="pwd">Password</label>
                        <input type="text" class="form-control" name="password" id="pwd" placeholder="Пароль">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Register</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>