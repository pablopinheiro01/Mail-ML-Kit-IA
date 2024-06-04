package com.alura.mail.samples

import com.alura.mail.model.Email
import com.alura.mail.model.User
import java.util.UUID

const val DEFAULT_LOCAL_EMAIL_NAME = "Você"


class EmailDao {
    fun getEmails(): List<Email> {
        return listOfEmails.sortedByDescending { it.time }
    }

    fun getEmailById(id: String): Email? {
        return listOfEmails.firstOrNull { it.id == id }
    }

    private val listOfEmails = listOf(
        Email(
            id = "6",
            subject = "Respostas inteligentes",
            content = "Fala aí John, tudo bem?\nEsse é o local que falei: 221B Baker Street, Reino Unido.\n" +
                    "Vejo você lá às 16:19, qualquer coisa pode ligar nesse número: 4002-8922 ou manda msg" +
                    "por aqui: teste@gmail.com.\nNa dúvida, dá uma olhada no https://www.alura.com.br/ " +
                    "O valor valor do metro aqui é £ 2,78 que dá uns R$ 1.500 reais 🤣 \n" +
                    "Ah mais uma coisa, não esquece meus US$100, meu IBAN é: GB15MIDL40051512345678",
            time = 1701195661310,
            color = 0xFFE77AAF,
            user = User("Anya Freitas"),
        ),
        Email(
            id = "-5",
            subject = "Feliz aniversário",
            content = "Feliz aniversário 🥳🥳🥳",
            time = 1697824497961,
            color = 0xFF5F96F5,
            user = User("Daniel Dias"),
            replies = listOf(
                Email(
                    id = "-6",
                    subject = "",
                    content = "Obrigado!",
                    time = 1697824497962,
                    color = 0xFFFF0057,
                    user = User(DEFAULT_LOCAL_EMAIL_NAME),
                ),
                Email(
                    id = "-7",
                    subject = "Re: Feliz aniversário",
                    content = "Vai ter bolo?👀",
                    time = 1697824497961,
                    color = 0xFF5F96F5,
                    user = User("Daniel Dias")
                )
            )
        ),
        Email(
            id = "-3",
            subject = "Amanhã em York Shin",
            content = "Tudo certo para amanhã as 10:00?",
            time = 1701054000000,
            color = 0xFF60AF71,
            user = User("Leo Rio"),
            replies = listOf(
                Email(
                    id = "-4",
                    subject = "",
                    content = "Sim, te encontro aonde?",
                    time = 1697824497962,
                    color = 0xFFFF774B,
                    user = User(DEFAULT_LOCAL_EMAIL_NAME),
                )
            )
        ),
        Email(
            id = "-2",
            subject = "Gostosuras ou travessuras?",
            content = "Feliz halloween 🎃\nPreprados para gostosuras e travessuras?",
            time = 1698721200000,
            color = 0xFFFF774B,
            user = User("Jack Skellington")
        ),
        Email(
            id = "-1",
            subject = "I'm back!",
            content = "My flight is LX373, please pick me up at 8am tomorrow.",
            time = 1697824497961,
            color = 0xFFE43B75,
            user = User("Mrs. Hudson")
        ),
        Email(
            id = "1",
            subject = "Idioma não traduzivel de exemplo",
            content = "Isibonelo solimi olungahumusheki",
            time = 1982949380000,
            color = 0xFF000000,
            user = User("* Dr Who")
        ),
        Email(
            id = "2",
            subject = "Migrando para o Jetpack Compose",
            content = "O Jetpack Compose foi desenvolvido com interoperabilidade de visualização desde o início. Com essa funcionalidade, você pode migrar seu app baseado em visualização para o Compose e ainda criar novos recursos. Para migrar para o Compose, recomendamos uma migração incremental em que esse sistema e as visualizações são usados juntos na base de código até que o app passe a usar o Compose totalmente.",
            time = 1697584497961,
            color = 0xFF5F96F5,
            user = User("Anderson Silva")
        ),
        Email(
            id = "30",
            subject = "Migrating to Jetpack Compose",
            content = "Jetpack Compose was designed with View interoperability right from the start. This functionality means you can migrate your existing View-based app to Compose while still being able to build new features. To migrate to Compose, we recommend an incremental migration where Compose and Views co-exist in your codebase until your app is fully in Compose.",
            time = 1697584497961,
            color = 0xFF9B5FF5,
            user = User("Leonard Hofstadter")
        ),
        Email(
            id = "3",
            subject = "Migration zu Jetpack Compose",
            content = "Jetpack Compose wurde von Grund auf mit Visualisierungsinteroperabilität entwickelt.  Mit dieser Funktionalität können Sie Ihre ansichtsbasierte App nach Compose migrieren und neue Funktionen erstellen.  Für die Migration zu Compose empfehlen wir eine inkrementelle Migration, bei der Compose und Ansichten zusammen in der Codebasis verwendet werden, bis Ihre App Compose vollständig nutzt.",
            time = 1697464497961,
            color = 0xFFDAA844,
            user = User("Ulrich Nielsen")
        ),
        Email(
            id = "4",
            subject = "Переход на Jetpack Compose",
            content = "Jetpack Compose с самого начала разрабатывался с учетом совместимости представлений.  Эта функция означает, что вы можете перенести свое приложение на основе Compose без необходимости компилировать новые функции.  Чтобы перейти на Compose, мы рекомендуем выполнить инкрементную миграцию, при которой Compose и представления сосуществуют в базе кода до тех пор, пока приложение не будет полностью переведено в Compose.",
            time = 1697344497961,
            color = 0xFFFE8966,
            user = User("Roy Mustang")
        ),
        Email(
            id = "7",
            subject = "Ratatouille - Le Festin",
            content = "Les rêves des amoureux sont comme le bon vin\n" +
                    "Ils donnent de la joie ou bien du chagrin\n" +
                    "Affaibli par la faim, je suis malheureux\n" +
                    "Volant en chemin tout ce que je peux\n" +
                    "Car rien n'est gratuit dans la vie\n" +
                    "\n" +
                    "L'espoir est un plat bien trop vite consommé\n" +
                    "À sauter les repas je suis habitué\n" +
                    "Un voleur, solitaire et triste à nourrir\n" +
                    "À nous, je suis amer, je veux réussir\n" +
                    "Car rien n'est gratuit dans la vie\n" +
                    "\n" +
                    "Jamais on ne me dira\n" +
                    "Que la course aux étoiles\n" +
                    "Ça n'est pas pour moi\n" +
                    "Laissez-moi vous émerveiller\n" +
                    "Et prendre mon envol\n" +
                    "Nous allons en fin nous régaler\n" +
                    "\n" +
                    "La fête va enfin commencer\n" +
                    "Et sortez les bouteilles, finis les ennuis\n" +
                    "Je dresse la table, de ma nouvelle vie\n" +
                    "Je suis heureux à l'idée de ce nouveau destin\n" +
                    "Une vie à me cacher et puis libre enfin\n" +
                    "Le festin est sur mon chemin\n" +
                    "Une vie à me cacher et puis libre enfin\n" +
                    "Le festin est sur mon chemin",
            time = 1697344497961,
            color = 0xFF60AF71,
            user = User("Alfredo Linguini")
        ),
        Email(
            id = "5",
            subject = "ಕನ್ನಡದ ಬಗ್ಗೆ ಇನ್ನಷ್ಟು ತಿಳಿಯಿರಿ",
            content = "ಕನ್ನಡವು ಭಾರತದಿಂದ ದಕ್ಷಿಣದ ದ್ರಾವಿಡ ಭಾಷೆಯಾಗಿದೆ, ಪ್ರಸ್ತುತ ಸಕ್ರಿಯ ಸ್ಥಾನಮಾನವನ್ನು ಹೊಂದಿದೆ, ಬೆಂಗಳೂರಿನಂತಹ ನಗರಗಳಲ್ಲಿ ದೈನಂದಿನ ಸಂವಹನಕ್ಕಾಗಿ ಬಳಸಲಾಗುವ ಮುಖ್ಯ ಭಾಷೆಯಾಗಿದೆ. 2011 ರಲ್ಲಿ ಪ್ರಪಂಚದಾದ್ಯಂತ 58 ಮಿಲಿಯನ್\u200Cಗಿಂತಲೂ ಹೆಚ್ಚು ಭಾಷೆಯನ್ನು ಮಾತನಾಡುವವರು ಇದ್ದರು, ಅವರಲ್ಲಿ ಸರಿಸುಮಾರು 43 ಮಿಲಿಯನ್ ಜನರು ಸ್ಥಳೀಯ ಭಾಷಿಕರು ಮತ್ತು 15 ಮಿಲಿಯನ್ ಜನರು ಎರಡನೇ ಅಥವಾ ಮೂರನೇ ಭಾಷೆಯಾಗಿದ್ದಾರೆ.",
            time = 1697224497961,
            color = 0xFF5F96F5,
            user = User("Jane Doe")
        ),
        Email(
            id = "6",
            subject = "Ratatouille - Le Festin",
            content = "Jetpack Compose è stato progettato fin dall'inizio con l'interoperabilità di View. Questa funzionalità significa che puoi eseguire la migrazione della tua app esistente basata sulle visualizzazioni a Compose pur continuando a creare nuove funzionalità. Per eseguire la migrazione a Compose, ti consigliamo di eseguire una migrazione incrementale in cui Compose e Views coesistono nel tuo codebase finché l'app non sarà completamente in Compose.",
            time = 1697104497961,
            color = 0xFFF55F5F,
            user = User("Silvano Sheen")
        ),
        Email(
            id = "8",
            subject = "Migrer des thèmes XML vers Compose",
            content = "Jetpack Compose 在設計之初就考慮了 View 互通性。這項功能意味著您可以將現有的 View 應用程式遷移至 Compose，同時仍然可以建構新功能。如要遷移至 Compose，建議採用逐步遷移，遷移期間，Compose 和 View 在程式碼集中共存，直至應用程式完全遷移至 Compose。",
            time = 1696984497961,
            color = 0xFF5F66F5,
            user = User("Ye Xiu")
        ),
        Email(
            id = "9",
            subject = "Урок Android с ИИ",
            content = "Jetpack Compose был создан с учетом совместимости визуализации с нуля. Благодаря этой функции вы можете перенести свое приложение на основе представлений в Compose и создавать новые функции. Чтобы перейти на Compose, мы рекомендуем выполнить инкрементную миграцию, при которой Compose и представления используются вместе в базе кода, пока ваше приложение полностью не будет использовать Compose.",
            time = 1696864497961,
            color = 0xFFF55FEE,
            user = User("Иван")
        ),
        Email(
            id = "10",
            subject = "Ein neuer Android-Kurs mit KI",
            content = "Jetpack Compose는 처음부터 뷰 상호 운용성을 고려하여 설계되었습니다. 이 기능을 사용하면 기존 뷰 기반 앱을 Compose로 이전하면서도 계속 새 기능을 빌드할 수 있습니다. Compose로 이전하려면 앱이 Compose에 완전히 이전될 때까지 Compose와 뷰가 코드베이스에 공존하는 증분 이전을 사용하는 것이 좋습니다.",
            time = 1696744497961,
            color = 0xFF9B5FF5,
            user = User("Han Tae-sul")
        ),
        Email(
            id = "11",
            subject = "Un nouveau cours Android avec IA",
            content = "Jetpack Compose, en başından itibaren View birlikte çalışabilirliği olacak şekilde tasarlanmıştır. Bu işlev, yeni özellikler oluşturmaya devam ederken mevcut Görüntüleme tabanlı uygulamanızı E-posta Yazma'ya taşıyabileceğiniz anlamına gelir. E-posta Yaz'a geçiş yapmak için uygulamanız tamamen Compose'a geçene kadar kod tabanınızda Oluşturma ve Görünümler'in bir arada bulunduğu artımlı bir taşıma işlemi yapmanızı öneririz.",
            time = 1696624497961,
            color = 0xFFF55F5F,
            user = User("Hector Salamanca")
        ),
        Email(
            id = "12",
            subject = "A New Android AI Course",
            content = "Jetpack Compose は、ビューの相互運用性を最初から考慮して設計されています。この機能で、既存のビューベースのアプリを Compose に移行しつつ、新しい機能を引き続きビルドできます。Compose に移行する場合、アプリが完全に Compose に移行されるまで、コードベースに Compose と View を共存させる増分移行をおすすめします。",
            time = 1696504497961,
            color = 0xFFDAA844,
            user = User("Akihiko Kayaba")
        ),
        Email(
            id = "13",
            subject = "Новый курс по искусственному интеллекту",
            content = "تم تصميم Jetpack Compose لتتيح إمكانية التشغيل التفاعلي للعرض منذ البداية. تعني هذه الوظيفة أنه يمكنك نقل تطبيقك الحالي المستند إلى العرض إلى ميزة \"الكتابة\" مع الاستمرار في إنشاء ميزات جديدة. لنقل البيانات إلى ميزة \"الإنشاء\"، ننصحك بإجراء عملية نقل تزايدية حيث يتواجد كل من \"إنشاء\" و\"طرق العرض\" في قاعدة الرموز حتى يصبح التطبيق جاهزًا بالكامل في \"الكتابة\".",
            time = 1696384497961,
            color = 0xFF9B5FF5,
            user = User("Maruan Labibe")
        ),
        Email(
            id = "14",
            subject = "新的人工智能Android课程",
            content = "Ngay từ đầu, Jetpack Compose được thiết kế với trọng tâm là khả năng tương tác của Khung hiển thị. Nhờ có chức năng này, bạn có thể di chuyển ứng dụng dựa trên Khung hiển thị hiện có sang Compose mà vẫn có thể tạo các tính năng mới. Để di chuyển sang Compose, bạn nên di chuyển lần lượt tại vị trí Compose và Khung hiển thị cùng tồn tại trong cơ sở mã cho đến khi ứng dụng của bạn hoàn toàn chuyển sang Compose.",
            time = 1696504497961,
            color = 0xFFF55F5F,
            user = User("Tam Then")
        ),
        Email(
            id = "15",
            subject = "Migrar a la Jetpack Compose",
            content = "Jetpack Compose se diseñó con la interoperabilidad de vistas desde el principio. Esta funcionalidad significa que puedes migrar tu app basada en vistas a Compose sin dejar de compilar funciones nuevas. Para migrar a Compose, recomendamos una migración incremental en la que Compose y las vistas coexistan en la base de código hasta que la app esté completamente en Compose.",
            time = 1696384497961,
            color = 0xFF5FD4F5,
            user = User("Dalí Sergio")
        ),
        Email(
            id = "16",
            subject = "Un nuevo curso de Android con IA",
            content = "¡Hola! ¿Cómo estás? Estamos aquí para informarte que el curso de Android tiene una promoción. ¡Date prisa y asegura tu lugar ya! ",
            time = 1696264497961,
            color = 0xFF5FD4F5,
            user = User("Carlos")
        ),
        Email(
            id = "17",
            subject = "دورة جديدة في الذكاء الاصطناعي لأنظمة Android",
            content = "مرحبًا! نحن متحمسون لإبلاغك عن دورتنا الجديدة في الذكاء الاصطناعي لأنظمة Android. هذه فرصة رائعة لتوسيع معرفتك.",
            time = 1696144497961,
            color = 0xFFF55FEE,
            user = User("عبد الله")
        ),
        Email(
            id = "18",
            subject = "新しいAndroid AIコース",
            content = "こんにちは！ 新しいAndroid AIコースについてお知らせできることを嬉しく思います。これは知識を拡大する絶好の機会です。",
            time = 1696024497961,
            color = 0xFF5F96F5,
            user = User("太郎")
        ),
        Email(
            id = "19",
            subject = "Kotlin 개요",
            content = "Kotlin은 객체 지향 프로그래밍과 함수 프로그래밍을 모두 지원하는 오픈소스 정적 형식 지정 프로그래밍 언어입니다. Kotlin의 문법과 개념은 C#, 자바, Scala 등 다른 언어와 유사합니다. Kotlin은 수십 년에 걸쳐 개발되었으며 고유한 언어가 되는 것을 원치 않습니다. Kotlin에는 JVM(Kotlin/JVM), 자바스크립트(Kotlin/JS), 네이티브 코드(Kotlin/Native)를 타겟팅하는 변형이 있습니다.",
            time = 1695904497961,
            color = 0xFF9B5FF5,
            user = User("Han Tae-sul")
        ),
        Email(
            id = "20",
            subject = "Kotlin 概览",
            content = "Kotlin 是一种静态类型的开源编程语言，它既支持面向对象的编程，又支持函数式编程。Kotlin 提供的语法和概念与其他语言（包括 C#、Java 和 Scala 等等）类似。Kotlin 的目标并不是独树一帜，而是从几十年的语言发展中汲取灵感。它以变体的形式存在，这些变体以 JVM (Kotlin/JVM)、JavaScript (Kotlin/JS) 和原生代码 (Kotlin/Native) 为目标。",
            time = 1695784497961,
            color = 0xFFFE8966,
            user = User("Wang Yi")
        ),
        Email(
            id = "21",
            subject = "نظرة عامة على لغة Kotlin",
            content = "لغة Kotlin هي لغة برمجة مفتوحة المصدر ومكتوبة بشكل ثابت وتدعم كلاً من البرمجة الموجهة بالكائنات والوظائف. يقدم Kotlin بنية ومفاهيم مماثلة من لغات أخرى، بما في ذلك C# وجافا وScala وغير ذلك الكثير. ولا تهدف لغة Kotlin إلى أن تكون فريدة، بل إنها تستمد الإلهام من عقود من تطوير اللغة. تتوفّر هذه السمة في صيغ تستهدف JVM (Kotlin/JVM) وJavaScript (Kotlin/JS) وشفرة أصلية (Kotlin/Native).",
            time = 1695664497961,
            color = 0xFF5F96F5,
            user = User("Jamal Al-Fayyad")
        ),
        Email(
            id = "22",
            subject = "Kotlin'e genel bakış",
            content = "Kotlin, hem nesne odaklı hem de işlevsel programlamayı destekleyen, statik olarak yazılmış açık kaynaklı bir programlama dilidir. Kotlin; C#, Java ve Scala gibi pek çok dilden benzer söz dizimi ve kavramları sunar. Kotlin, benzersiz olmayı amaçlamaz. Onlarca yıllık dil geliştirme çalışmasından ilham alır. JVM (Kotlin/JVM), JavaScript (Kotlin/JS) ve yerel kodu (Kotlin/Native) hedefleyen varyantlarda mevcuttur.",
            time = 1695544497961,
            color = 0xFF5FF5A3,
            user = User("Zeynep Yılmaz")
        ),
        Email(
            id = "23",
            subject = "Tổng quan về Kotlin",
            content = "Kotlin là một ngôn ngữ lập trình nguồn mở, kiểu tĩnh, hỗ trợ cả lập trình chức năng lẫn hướng đối tượng. Kotlin cung cấp cú pháp và khái niệm tương tự trong các ngôn ngữ khác, bao gồm cả C#, Java và Scala cùng nhiều ngôn ngữ khác. Kotlin không phải là độc nhất – mà Kotlin lấy cảm hứng từ nhiều thập kỷ để phát triển ngôn ngữ. Mã này tồn tại trong các biến thể nhắm đến JVM (Kotlin/VM), JavaScript (Kotlin/JS) và mã gốc (Kotlin/mã gốc).",
            time = 1695424497961,
            color = 0xFF9B5FF5,
            user = User("Luan Nguyen")
        ),

        Email(
            id = "24",
            subject = "Panoramica di Kotlin",
            content = "Kotlin è un linguaggio di programmazione open source e di tipo statico che supporta la programmazione funzionale e orientata agli oggetti. Kotlin fornisce sintetizzazione e concetti simili di altri linguaggi, tra cui C#, Java e Scala, tra molti altri. L'obiettivo di Kotlin non è unico, ma si ispira da decenni di sviluppo del linguaggio. Esistono in diverse varianti che hanno come target JVM (Kotlin/JVM), JavaScript (Kotlin/JS) e codice nativo (Kotlin/Native).",
            time = 1695304497961,
            color = 0xFFF55F5F,
            user = User("Giovanni Solano")
        ),

        Email(
            id = "25",
            subject = "Descripción general de Kotlin",
            content = "Kotlin es un lenguaje de programación estático de código abierto que admite la programación funcional y orientada a objetos. Proporciona una sintaxis y conceptos similares a los de otros lenguajes, como C#, Java y Scala, entre muchos otros. No pretende ser único, sino que se inspira en décadas de desarrollo del lenguaje. Cuenta con variantes que se orientan a la JVM (Kotlin/JVM), JavaScript (Kotlin/JS) y el código nativo (Kotlin/Native).",
            time = 1695184497961,
            color = 0xFF5F96F5,
            user = User("Messi Lionel")
        ),
        Email(
            id = "26",
            subject = "XML temalarını Oluştur'a taşı",
            content = "Mevcut bir uygulamada Compose'u kullanıma sunduğunuzda, Oluştur ekranlarında MaterialTheme kullanmak için temalarınızı XML olarak taşımanız gerekir. Yani, uygulama temanız iki doğruluk kaynağına sahip olur: Görüntülemeye dayalı tema ve Oluştur teması. Stilinizde yaptığınız değişikliklerin birden çok yerde yapılması gerekir. Uygulamanız Compose'a tamamen taşındıktan sonra XML temanızı kaldırabilirsiniz.",
            time = 1626366000000L,
            color = 0xFFDAA844,
            user = User("Froid Curie")
        ),
        Email(
            id = "27",
            subject = "Νέο μάθημα Android με ΤΝ",
            content = "Γειά σας, πώς είστε; Θέλουμε να σας ενημερώσουμε ότι το μάθημα Android είναι σε προσφορά. Κλείστε τη θέση σας τώρα! ",
            time = 1694944497961,
            color = 0xFFDAA844,
            user = User("Σοφία")
        ),
        Email(
            id = "28",
            subject = "Un nuevo curso de Android con IA",
            content = "¡Hola! ¿Cómo estás? Estamos aquí para informarte que el curso de Android tiene una promoción. ¡Date prisa y asegura tu lugar ya! ",
            time = 1694824497961,
            color = 0xFF5FF5A3,
            user = User("Carlos")
        ),
        Email(
            id = "29",
            subject = "新しいAndroid AIコース",
            content = "こんにちは！ 新しいAndroid AIコースについてお知らせできることを嬉しく思います。これは知識を拡大する絶好の機会です。",
            time = 1694704497961,
            color = 0xFF5F96F5,
            user = User("太郎")
        )
    )

    private val listOfReplies1 = listOf(
        Email(
            id = "r1",
            subject = "Respotas exemplo 1",
            content = "",
            time = 1699220584791,
            color = 0xFF000000,
            user = User("Dr Who")
        ),
        mountLocalEmail("").copy(id = "r2")
    )

    fun mountLocalEmail(content: String): Email {
        return Email(
            subject = "",
            content = content,
            id = UUID.randomUUID().toString(),
            time = System.currentTimeMillis(),
            color = 0XFF8F4F24,
            user = User(DEFAULT_LOCAL_EMAIL_NAME),
        )
    }
}