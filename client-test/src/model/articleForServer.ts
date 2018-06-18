import {ArticleIdEntity} from "./articleId";

export class ArticleForServerEntity {
    articleId: ArticleIdEntity;
    title: string;
    mainText: string;

    public constructor() {
        this.articleId = null;
        this.title = "";
        this.mainText = "";
    }
}
