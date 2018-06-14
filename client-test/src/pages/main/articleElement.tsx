import * as React from "react";
import {ArticleEntity} from "../../model/article";
import {Link} from "react-router";

interface Props {
    article: ArticleEntity;
}

export const ArticleElementComponent = (props: Props) => {
    let pass = "/article/"+props.article.id;
    return (
        <div className="card">
                <div className="container">
                    <h4><b>{props.article.title}</b></h4>
                    <Link to={pass}>Student Detail</Link>
                </div>
        </div>
);
};
