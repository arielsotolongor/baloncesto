<%--
  Created by IntelliJ IDEA.
  User: Ariel
  Date: 20/02/2024
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="codigo.Jugador" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Votación mejor jugador liga ACB</title>
    <link href="estilos.css" rel="stylesheet" type="text/css"/>
</head>
<body class="resultado">
<h1 class="titulo">Votación al mejor jugador de la liga ACB</h1>

<hr>
<%
    List<Jugador> jugadores = (List<Jugador>) session.getAttribute("jugadores");
%>

<table>
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Votos</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Jugador jugador : jugadores) {
    %>
    <tr>
        <td><%= jugador.getNombre() %>
        </td>
        <td><%= jugador.getVotos() %>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<br>
<br> <a href="index.html"> Ir al comienzo</a>
</body>
</html>