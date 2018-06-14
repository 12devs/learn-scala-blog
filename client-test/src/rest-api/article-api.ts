import {ArticleEntity} from "../model/article";
import {articlesMockData} from "./mock-data";

class ArticleApi {
    articlesData: ArticleEntity[];

    constructor() {
        // Let"s the mockdata whenever the singleton is instatiated
        // and the play with the inmemory array
        this.articlesData = articlesMockData;

    }

    loadArticleList(): Promise<ArticleEntity[]> {
        return Promise.resolve(this.articlesData);
    }

    loadArticle(id: number): Promise<ArticleEntity> {
        return Promise.resolve(this.articlesData[id]);
    }

}

export const articleApi = new ArticleApi();
