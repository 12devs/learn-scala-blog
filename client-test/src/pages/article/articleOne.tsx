import * as React from "react";
import {ArticleEntity} from "../../model/article";
import {match} from "react-router";
import {articleApi} from "../../rest-api/article-api";
import {articlesMockData} from "../../rest-api/mock-data";

interface Props {
    articleList: ArticleEntity;
    getArticleList: () => void;
    params: { id: string };
}

interface MyState {
    components: ArticleEntity;
}

export class ArticleOneComponent extends React.Component<Props, MyState> {

    componentDidMount() {
        this.props.getArticleList();
    }

    render() {
        return (
            <div>
                <h2>{articlesMockData[this.props.params.id].title}</h2>
                <h2>{this.props.params.id}</h2>
                {articlesMockData[this.props.params.id].mainText}
                <br/>


            </div>
        );
    }
}

