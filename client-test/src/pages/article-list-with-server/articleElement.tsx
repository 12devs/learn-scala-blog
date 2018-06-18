import * as React from "react";
import {Link} from "react-router";
import {ArticleForServerEntity} from "../../model/articleForServer";

interface Props {
    article: ArticleForServerEntity;
}


export const ArticleElementComponent = (props: Props) => {

    return (
        <div>
            <h1>{props.article.title}</h1>
            {props.article.mainText}
            {props.article.articleId.id}
        </div>
    );
};
