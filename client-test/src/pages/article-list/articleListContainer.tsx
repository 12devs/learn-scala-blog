import { connect } from 'react-redux';
import { articleListRequestStartedAction } from "./actions/articleListRequestStarted";
import { ArticleListComponent } from './articleList';

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

export const ArticleListContainer = connect(
                                   mapStateToProps
                                  ,mapDispatchToProps
                                )(ArticleListComponent);
