import * as React from "react";
import {ArticleEntity} from "../../model/article";
import {ArticleElementComponent} from "./articleElement";

interface Props {
    articleList: ArticleEntity[];
    getArticleList: () => void;
}

export class ArticleComponent extends React.Component<Props, {}> {

    componentDidMount() {
        this.props.getArticleList();
    }

    render() {
        return (
            <div>
                <h2>I"m the Main page</h2>
                {

                    this.props.articleList.map((article: ArticleEntity) =>
                        <ArticleElementComponent key={article.id} article={article}/>
                    )
                }
                <br/>
            </div>
        );
    }
}

