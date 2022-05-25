<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" lang="English">

<link rel="stylesheet" th:href="@{../static/css/style.css}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
    body {
        background-color: #1F2833;
    }

    h1 {
        text-align: center;
        color: #66FCF1;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 75px;
    }

    button {
        border-radius: 15px;
        background-color: #45A29E; /* Green */
        border: none;
        color: #C5C6C7;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 25px;
    }

    .grid-container {
        display: grid;
        grid-template-columns: auto auto auto auto;
        padding-top: 10px;
    }

    /* Start Production button*/
    .grid-item1 {
        background-color: #1F2833;
        padding-top: 10px;
        padding-bottom: 75px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 30px;
        text-align: center;
    }

    /* Stop Production button*/
    .grid-item2 {
        background-color: #1F2833;
        padding-top: 10px;
        padding-bottom: 75px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 30px;
        text-align: center;
    }

    /* Whitespace for aesthetic*/
    .grid-item3 {
        background-color: #1F2833;
        padding-top: 1px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 30px;
        text-align: center;
        grid-column: 1 /  2;
    }

    .grid-item-warehouse {
        background-color: #1F2833;
        border: 1px solid #45A29E;
        padding: 20px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 40px;
        text-align: center;
        color: #C5C6C7;
    }

    .grid-item-agv {
        background-color: #1F2833;
        border: 1px solid #45A29E;
        padding: 20px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 40px;
        text-align: center;
        color: #C5C6C7;
    }

    .grid-item-assembly {
        background-color: #1F2833;
        border: 1px solid #45A29E;
        padding: 20px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 40px;
        text-align: center;
        color: #C5C6C7;
    }

    /* All other grid items can be shown as the same */
    .grid-item {
        background-color: #1F2833;
        border: 1px solid #45A29E;
        padding: 40px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 30px;
        text-align: center;
        color: #C5C6C7;
    }
</style>
<script>
    $(document).ready(function () {

        var currentOperationWH = $("#COWH")
        var currentOperationagv = $("#COAGV")
        var currentOperationas = $("#COAS")

        var connectionStatusWH= $("#CNWH")
        var connectionStatusAGV= $("#CNAGV")
        var connectionStatusAS = $("#CNAS")

        var machineStateWH = $("#MSWH")
        var machineStateAGV = $("#MSAGV")
        var machineStateAS = $("#MSAS")



        var url = "http://localhost:8080/sse";
        var listener = new EventSource(url)
        listener.addEventListener("update", function (e) {
            var json = JSON.parse(e.data);
            // var agv = json["agv"]
            // var warehouse = json["warehouse"]
            // var assemblystation = json["assemblystation"];


            console.log(json["test"])
            currentOperationWH.text(json["test"])
            currentOperationagv.html(e)
            currentOperationas.html(e)
            connectionStatusWH.html(e)
            connectionStatusAGV.html(e)
            connectionStatusAS.html(e)
            machineStateWH.html(e)
            machineStateAGV.html(e)
            machineStateAS.html(e)
        })

        $("#startProductionPattern").on("click",function (e){
            e.preventDefault();
            $.ajax("/startProduction")
        })

    })
</script>
<body>

<h1>THE DRONE ZONE</h1>

<!-- Setup grid container and declare all grid items,
numbers represent the cell number -->

<div class="grid-container">
    <div class="grid-item1">
        <button id="startProductionPattern" type="button">Start Production</button>
    </div>
    <div class="grid-item2">
        <button type="button">Stop Production</button>
    </div>
    <div class="grid-item3"></div>
    <div class="grid-item-warehouse">Warehouse</div>
    <div class="grid-item-agv">AGV</div>
    <div class="grid-item-assembly">Assembly Station</div>
    <div class="grid-item">Connection Status:</div>
    <div id="CNWH" class="grid-item">7</div>
    <div id="CNAGV" class="grid-item">8</div>
    <div id="CNAS" class="grid-item">9</div>
    <div class="grid-item">Machine State:</div>
    <div id="MSWH" class="grid-item">11</div>
    <div id="MSAGV" class="grid-item">12</div>
    <div id="MSAS" class="grid-item">13</div>
    <div class="grid-item">Current Operation:</div>
    <div id="COWH" class="grid-item">15</div>
    <div id="COAGV" class="grid-item">16</div>
    <div id="COAS" class="grid-item">17</div>
</div>