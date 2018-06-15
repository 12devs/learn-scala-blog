import * as React from "react";
import {ArticleEntity} from "../../model/article";
import {Link} from "react-router";

interface Props {
    article: ArticleEntity;
}


export const ArticleElementComponent = (props: Props) => {
    let pass = "/article/" + (props.article.id - 1);
    let src = "https://unsplash.it/400/" + 300 + "?image=4" + props.article.id;
    const changeColor = () => {
        if (props.article.title.length % 2 == 0)
            return "card__overlay card__overlay--blue";
        return "card__overlay card__overlay--indigo";
    };

    return (
        <div className="grid">
            <div className="card">
                <div className="card__image">
                    <img src={src} alt=""/>

                    <div className={changeColor()}>
                        <div className="card__overlay-content">
                            <Link className="card__title" to={pass}>{props.article.title}</Link>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
