<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1>Daily Analytics</h1>
    <canvas id="dailyChart"></canvas>
    <#if dailyCounts??>
    <script>
        const ctx = document.getElementById('dailyChart').getContext('2d');
        const dailyCounts =[${dailyCounts?join(",")}];

        const dailyChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: Array.from({length: 31}, (v, k) => k + 1),
                datasets: [{
                    label: 'Заказов в день',
                    data: dailyCounts,
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
