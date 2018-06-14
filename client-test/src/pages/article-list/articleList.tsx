import * as React from "react";
import { ArticleEntity } from "../../model/article";
import { ArticleTableComponent } from "./components/articleTable";

interface Props {
  articleList: ArticleEntity[];
  getArticleList: () => void;
}

export class ArticleListComponent extends React.Component<Props, {}> {

  componentDidMount() {
    this.props.getArticleList();
  }

  render() {
    return (
      <div>
        <h2>I"m the Article page</h2>
        <br/>
        <ArticleTableComponent articleList={this.props.articleList}/>
      </div>
    );
  }
}

