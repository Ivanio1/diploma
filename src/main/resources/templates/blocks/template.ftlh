<#macro template user>
    <!doctype html>
    <html lang="ru">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="/static/css/style.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <#if logo??>
            <link rel="icon" href="/logo">
        <#else>
            <link rel="icon" href="https://img.icons8.com/wired/64/car-service.png">
        </#if>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css">

        <#if appearanceSettings.siteName??>
            <title>${appearanceSettings.siteName}</title>
        <#else>
            <title>CRM</title>
        </#if>
    </head>
    <body>
    <style>
        .btn-dark {
            background-color: <#if appearanceSettings.titleColor??>${appearanceSettings.titleColor} !important;
        </#if>;
            color: <#if appearanceSettings.textColor??>${appearanceSettings.textColor} !important;
        </#if>;
        }

        .table th {
            background-color: <#if appearanceSettings.titleColor??>${appearanceSettings.titleColor} !important;
        </#if>;
        }

        .table td {
            background-color: <#if appearanceSettings.tdColor??>${appearanceSettings.tdColor} !important;
        </#if>;
        }

        .table tr:hover td {
            background-color: <#if appearanceSettings.tdHoverColor??>${appearanceSettings.tdHoverColor} !important;
        </#if>;
        }

        body {
            font-family: <#if appearanceSettings.fontSelect??>${appearanceSettings.fontSelect} !important;
        </#if>;
        }
    </style>
    <nav class="navbar .navbar-dark bg-dark"
         style="<#if appearanceSettings.titleColor??> background-color: ${appearanceSettings.titleColor} !important;</#if>"
    >
        <div class="container-fluid">
            <#if logo??>
                <img src="/logo" width="32" height="32" class="rounded-circle">
            </#if>
            <a href="/">
                <#if appearanceSettings.siteName??>
                    <span class="navbar-brand mb-0 h1" style="color: white">${appearanceSettings.siteName}</span>
                <#else>
                    <span class="navbar-brand mb-0 h1">Car service CRM</span>
                </#if>
            </a>
            <#if user.email??>
                <div class="dropdown text-end">
                    <a href="/profile">
                        <img src="/static/images/avatar.png" alt="mdo"
                             width="32" height="32" class="rounded-circle">
                    </a>
                </div>
            <#else>
                <button class="btn btn-light my-2 my-sm-0" type="button"
                        onclick="window.location.href = '/login'">
                    Войти
                </button>
            </#if>
        </div>
    </nav>
    <div class="container mt-4">
        <#nested/>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10.15.7/dist/sweetalert2.all.min.js"></script>
    </body>
    </html>
</#macro>
