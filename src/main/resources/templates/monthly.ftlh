<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1>Monthly Analytics</h1>
    <canvas id="dailyChart"></canvas>
    <#if monthlyCounts??>
    <script>
        const ctx = document.getElementById('dailyChart').getContext('2d');
        const monthlyCounts =[${monthlyCounts?join(",")}];
        const monthNames = [
            "Январь", "Февраль", "Март", "Апрель",
            "Май", "Июнь", "Июль", "Август",
            "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        ];

        const dailyChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: monthNames,
                datasets: [{
                    label: 'Заказов в месяц',
                    data: monthlyCounts,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>
    <#else>
        <h3>Нет заказов</h3>
    </#if>
</@t.template>
