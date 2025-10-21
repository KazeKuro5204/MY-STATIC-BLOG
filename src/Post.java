public class Post {
    private String title;
    private String summary;
    private String content;
    private String category;
    private String slug;

    public Post(String title, String summary, String content, String category, String slug) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.category = category;
        this.slug = slug;
    }

    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public String getSlug() { return slug; }
}
