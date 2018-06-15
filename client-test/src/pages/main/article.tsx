import * as React from "react";
import {ArticleEntity} from "../../model/article";
import {ArticleElementComponent} from "./articleElement";
import './article.scss'

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
                <div className="site__wrapper">
                    {

                        this.props.articleList.map((article: ArticleEntity) =>
                            <ArticleElementComponent key={article.id} article={article}/>
                        )
                    }
                    <br/>
                </div>
            </div>
        );
    }
}

