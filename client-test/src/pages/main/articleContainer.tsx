import { connect } from 'react-redux';
import { articleListRequestStartedAction } from "./actions/articleListRequestStarted";
import { ArticleComponent } from './article';

const mapStateToProps = (state) => {
    return {
        articleList: state.articleReducer.articlesList
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getArticleList: () => dispatch(articleListRequestStartedAction()),
    }
}

export const ArticleContainer = connect(
    mapStateToProps
    ,mapDispatchToProps
)(ArticleComponent);
