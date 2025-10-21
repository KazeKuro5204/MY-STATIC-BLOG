import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BlogGenerator {
    public static void main(String[] args) throws IOException {
        List<Post> posts = List.of(
            new Post("Lập trình socket trong Java", "Hướng dẫn tạo socket server-client bằng Java.", "<p>Nội dung chi tiết về Socket trong Java...</p>", "Java", "lap-trinh-socket-trong-java"),
            new Post("Giao tiếp TCP/IP với Server", "Tổng quan TCP/IP và cách ứng dụng.", "<p>Nội dung chi tiết về TCP/IP...</p>", "Java", "giao-tiep-tcp-ip"),
            new Post("Đọc/Ghi file trong Java", "Các phương pháp xử lý file trong Java.", "<p>Nội dung chi tiết về file I/O...</p>", "Java", "doc-ghi-file-trong-java"),
            new Post("Fetch API trong JavaScript", "Sử dụng Fetch để gọi API.", "<p>Nội dung chi tiết về Fetch...</p>", "JavaScript", "fetch-api-trong-js"),
            new Post("Promise & Async/Await", "Quản lý bất đồng bộ trong JS.", "<p>Nội dung chi tiết về Promise...</p>", "JavaScript", "promise-async-await"),
            new Post("Xử lý sự kiện trong DOM", "Event handling cơ bản và nâng cao.", "<p>Nội dung chi tiết về DOM events...</p>", "JavaScript", "xu-ly-su-kien-dom"),
            new Post("Kết nối Backend & Frontend", "Cách kết nối REST API với frontend.", "<p>Nội dung chi tiết về kết nối...</p>", "Fullstack", "ket-noi-backend-frontend"),
            new Post("RESTful API là gì?", "Nguyên tắc xây dựng RESTful.", "<p>Nội dung chi tiết về REST...</p>", "Backend", "restful-api-la-gi"),
            new Post("Kinh nghiệm học lập trình mạng", "Tips & best practices khi học mạng.", "<p>Nội dung chia sẻ kinh nghiệm...</p>", "General", "kinh-nghiem-hoc-lap-trinh-mang")
        );

        Path outDir = Paths.get("output");
        Path postsDir = outDir.resolve("posts");
        Files.createDirectories(postsDir);

        String homeTemplate = readTemplate("templates/home.html");
        String blogTemplate = readTemplate("templates/blog.html");
        String postTemplate = readTemplate("templates/post-template.html");

        for (Post p : posts) {
            String single = postTemplate
                .replace("%%TITLE%%", escapeHtml(p.getTitle()))
                .replace("%%CATEGORY%%", escapeHtml(p.getCategory()))
                .replace("%%CONTENT%%", p.getContent());
            Files.writeString(postsDir.resolve(p.getSlug() + ".html"), single);
        }

        StringBuilder cards = new StringBuilder();
        for (Post p : posts) {
            cards.append(String.format(
                "<div class='col-md-4 mb-4'>\n" +
                "  <div class='card p-3 h-100 shadow-sm'>\n" +
                "    <h5>%s</h5>\n" +
                "    <p>%s</p>\n" +
                "    <span class='badge bg-secondary'>%s</span><br><br>\n" +
                "    <a href='posts/%s.html' class='btn btn-sm btn-outline-primary'>Đọc tiếp</a>\n" +
                "  </div>\n</div>\n",
                escapeHtml(p.getTitle()), escapeHtml(p.getSummary()), escapeHtml(p.getCategory()), p.getSlug()
            ));
        }
        String blogPage = blogTemplate.replace("<!--POST_CARDS-->", cards.toString());
        Files.writeString(outDir.resolve("blog.html"), blogPage);

        String homeContent = "<h1>👋 Xin chào, mình là KazeKuro</h1>\n" +
                             "<p class='lead'>Mình là sinh viên yêu thích lập trình mạng, Java & JavaScript. Đây là blog chia sẻ kiến thức cá nhân.</p>\n" +
                             "<a class='btn btn-primary' href='blog.html'>Đọc blog</a>";
        String homePage = homeTemplate.replace("<!--HOME_CONTENT-->", homeContent);
        Files.writeString(outDir.resolve("index.html"), homePage);

        System.out.println("✅ Blog static đã tạo trong thư mục 'output'");
    }

    private static String readTemplate(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
