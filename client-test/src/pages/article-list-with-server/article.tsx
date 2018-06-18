import * as React from "react";
import {ArticleElementComponent} from "./articleElement";
import {ArticleForServerEntity} from "../../model/articleForServer";
import {ArticleService} from "../../service/ArticleService";


interface Props {
    articleList: ArticleForServerEntity[];
    getArticleList: () => void;
}

interface MyState {
    articles: ArticleForServerEntity[];
}

export class ArticleComponent extends React.Component<Props, MyState> {
    state: MyState = {
        articles: []
    };
    componentDidMount() {
        this.refreshArticles()
    }

    private refreshArticles = () => {
        ArticleService.getArticles()
            .then(articles => {
                this.setState({articles});
            });
    };

    render() {
        return (
            <div>
                <div className="site__wrapper">

                    {

                        this.state.articles.map((article: ArticleForServerEntity) =>
                            <ArticleElementComponent key={article.articleId.id} article={article}/>
                        )
                    }

                    <br/>
                </div>
            </div>
        );
    }
}

