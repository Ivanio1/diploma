<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1>Weekly Analytics</h1>
    <canvas id="dailyChart"></canvas>
    <#if weeklyCounts??>
    <script>
        const ctx = document.getElementById('dailyChart').getContext('2d');
        const weeklyCounts =[${weeklyCounts?join(",")}];
        const labels = Array.from({length: weeklyCounts.length}, (_, i) => i + 1);
        const dailyChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Заказов в неделю',
                    data: weeklyCounts,
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
