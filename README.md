## Описание

Несложная игра "три в ряд". В начале каждого дня выдается приветственный бонус (нужно угадать где лежит бонус). После прохождения каждого уровня открывается следующий по сложности уровень. В ходе игры зарабатываются очки, на которые можно купить в игровом магазине статуэтку.

## О проекте
Архитектура проекта старается следовать принципам Clean architecture, и делится на модули:

* app - модуль, который знает обо всех модулях в проекте и выступает местом для хранения всех зависимостей (в классе MainApp)
* common - модуль для хранения общей на проект информации: расширений классов, сущностей для обмена данными между слоями ui, domain, data, настроек темы, и ресурсов
* data - модуль для работы с внутренней БД
* *-ui - модули для хранений экранов и вью моделей для них
* *-domain - модули для выполнения бизнес логики и подтягивания данных из слоя data в слой ui

## Скриншоты

<img src="https://github.com/askosarygin/LuckyFuHottei/assets/77168356/b18fd5aa-306c-425f-989b-eb0c3b8c47ab)" alt="drawing" width="200"/> 
<img src="https://github.com/askosarygin/LuckyFuHottei/assets/77168356/e876f0cc-0eca-4a56-a886-975f89a57730" alt="drawing" width="200"/> 
<img src="https://github.com/askosarygin/LuckyFuHottei/assets/77168356/807bc93c-f1d5-4a73-b245-bebd86a9b338" alt="drawing" width="200"/> 
<img src="https://github.com/askosarygin/LuckyFuHottei/assets/77168356/10497666-62a4-491e-8f67-cec7d1f74a82" alt="drawing" width="200"/>
<img src="https://github.com/askosarygin/LuckyFuHottei/assets/77168356/058ccc88-f913-46fe-9b0e-0218c1bf81d0" alt="drawing" width="200"/>

