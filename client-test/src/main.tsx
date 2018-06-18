import * as React from 'react';
import * as ReactDOM from 'react-dom';
import {createStore, applyMiddleware, compose} from 'redux';
import {Router, Route, IndexRoute, hashHistory} from 'react-router';
import {syncHistoryWithStore} from 'react-router-redux'
import reduxThunk from 'redux-thunk';
import {Provider} from 'react-redux';
import {reducers} from './reducers'
import {App} from './app';
import {LoginContainer} from './pages/login';
import {ArticleListContainer} from './pages/article-list';
import {ArticleContainer} from "./pages/main/articleContainer";
import {ArticleOneContainer} from "./pages/article";
import {ArticleComponent} from "./pages/article-list-with-server/article";


let store = createStore(
    reducers,
    compose(
        applyMiddleware(reduxThunk),
        window['devToolsExtension'] ? window['devToolsExtension']() : f => f
    )
);

const history = syncHistoryWithStore(hashHistory, store);

ReactDOM.render(
    <Provider store={store}>
        <div>
            <Router history={history}>
                <Route path="/" component={App}>
                    <IndexRoute component={LoginContainer}/>
                    <Route path="articles" component={ArticleContainer}/>
                    <Route path="login" component={LoginContainer}/>
                    <Route path="article-list" component={ArticleListContainer}/>
                    <Route path="article-list-for-server" component={ArticleComponent}/>
                    <Route path="article/:id" component={ArticleOneContainer}/>
                </Route>
            </Router>
        </div>
    </Provider>,
    document.getElementById('root')
);

