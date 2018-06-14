import * as React from "react";
import {ArticleEntity} from "../../../model/article";

interface Props {
    article: ArticleEntity;
}

export const ArticleRowComponent = (props: Props) => {
    return (
        <tr>
            <td>
                {(props.article.id)}
            </td>
            <td>
                <span>{props.article.title}</span>
            </td>
            <td>
                <span>{props.article.mainText}</span>
            </td>
        </tr>
    );
};
