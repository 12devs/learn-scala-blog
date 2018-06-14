import {connect} from 'react-redux';
import {articleListRequestStartedAction} from './actions/articleListRequestStarted';
import {ArticleOneComponent} from './articleOne';

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

export const ArticleOneContainer = connect(
    mapStateToProps
    , mapDispatchToProps
)(ArticleOneComponent);
