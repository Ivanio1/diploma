<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1>Настройки внешнего вида системы</h1>
    <form action="/admin/settings" method="post" enctype="multipart/form-data">
        <br/><br/>
        <div class="form-group">
            <label for="siteName">Название сайта:</label>
            <input type="text" id="siteName" name="siteName" required
                    <#if appearanceSettings.siteName??> placeholder=${appearanceSettings.siteName}</#if>
            >
        </div>
        <br/>
        <hr/>
        <div class="container-fluid" style="background-color: white; text-align:center; border-radius: 3px !important;">
            <div class="form-group">
                <label for="titleColor">Основной цвет сайта и цвет кнопок:</label>
                <input type="color" id="titleColor" name="titleColor" <#if appearanceSettings.titleColor??>
                    value=${appearanceSettings.titleColor}
                <#else> value="black"</#if>
                       required/>
                &nbsp
                <label for="tdColor">Цвет строк таблицы:</label>
                <input type="color" id="tdColor" name="tdColor"<#if appearanceSettings.tdColor??>
                value=${appearanceSettings.tdColor}
                        <#else> value="white"</#if>
                       required/>
                &nbsp
                <label for="tdHoverColor">Цвет строк таблицы при наведении:</label>
                <input type="color" id="tdHoverColor" name="tdHoverColor"<#if appearanceSettings.tdHoverColor??>
                value=${appearanceSettings.tdHoverColor}
                        <#else> value="white"</#if>
                       required/>
                &nbsp
                <label for="textColor">Цвет шрифта кнопок:</label>
                <input type="color" id="textColor" name="textColor"<#if appearanceSettings.textColor??>
                value=${appearanceSettings.textColor}
                        <#else> value="white"</#if>
                       required/>
            </div>
        </div>
        <br/>
        <hr/>
        <div class="form-group">
            <label for="image">Аватар сайта (ссылка на иконку с icons8.com):</label>
            <input type="text" id="image" name="image"
                    <#if appearanceSettings.image??> placeholder=${appearanceSettings.image}</#if>>
        </div>
        <hr/>
        <div class="form-group">
            <label for="fontSelect">Выберите шрифт:</label>
            <select id="fontSelect" name="fontSelect" onchange="updateFont()">
                <option value="Arial">Arial</option>
                <option value="Verdana">Verdana</option>
                <option value="Times New Roman">Times New Roman</option>
                <option value="Georgia">Georgia</option>
                <option value="Courier New">Courier New</option>
                <option value="Comic Sans MS">Comic Sans MS</option>
                <option value="Tahoma">Tahoma</option>
                <option value="Impact">Impact</option>
            </select>

            <div id="fontPreview">Текст для предварительного просмотра шрифта</div>

            <script>
                function updateFont() {
                    var select = document.getElementById("fontSelect");
                    var preview = document.getElementById("fontPreview");
                    preview.style.fontFamily = select.value;
                }
            </script>
        </div>

        <hr/>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-dark m-3">
            Сохранить
        </button>
    </form>

    <form action="/logo" method="post" enctype="multipart/form-data">
        <label for="file">Загрузите логотип:</label>
        <input type="file" name="file" required>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-dark m-3">
            Сохранить
        </button>
    </form>

</@t.template>
