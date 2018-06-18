import * as React from "react";
import {ArticleEntity} from "../../model/article";
import {articlesMockData} from "../../rest-api/mock-data";
import {ArticleOneCommentsComponent} from "./articleOneComments";
import {CommentEntity} from "../../model/comment";

interface Props {
    articleList: ArticleEntity;
    getArticleList: () => void;
    params: { id: string };
}


export class ArticleOneComponent extends React.Component<Props, {}> {

    componentDidMount() {
        this.props.getArticleList();
    }

    render() {
        return (
            <div>
                <h1 className="article-title">{articlesMockData[this.props.params.id].title}</h1>
                <h2>{this.props.params.id}</h2>
                <div className="main-text">{articlesMockData[this.props.params.id].mainText}</div>
                <br/>
                <h2>{
                    articlesMockData[this.props.params.id].comments.map((comment: CommentEntity) =>
                        <ArticleOneCommentsComponent comment={comment}/>
                    )
                }
                </h2>
            </div>
        );
    }
}

