import * as React from "react";
import { ArticleEntity } from "../../../model/article";
import { ArticleHeaderComponent } from "./articleHeader";
import { ArticleRowComponent } from "./articleRow";

interface Props {
  articleList: ArticleEntity[];
}

export const ArticleTableComponent = (props: Props) => {
  return (
    <table className="table">
      <ArticleHeaderComponent/>
      <tbody>
        {
          props.articleList.map((article: ArticleEntity) =>
            <ArticleRowComponent key={article.id} article= {article}/>
          )
        }
      </tbody>
    </table>
  );
};
