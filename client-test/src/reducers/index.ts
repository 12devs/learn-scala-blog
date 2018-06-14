import {combineReducers} from 'redux';
import {sessionReducer} from './session';
import {articleReducer} from "./article";
import {routerReducer} from 'react-router-redux'

export const reducers = combineReducers({
    sessionReducer,
    articleReducer,
    routing: routerReducer
});
