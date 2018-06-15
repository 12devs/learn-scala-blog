import * as React from 'react'
import {Link} from 'react-router'

export const App = (props: { children? }) => {
    return (
        <div>
            <header className="header">
                {' '}
                <Link className="logo" to="/articles">Blog Project</Link>
                <div className="header-right">
                    {' '}
                    <Link to="/">Login</Link>
                    {' '}
                    <Link to="/article-list">Article List</Link>
                </div>
            </header>
            <div style={{marginTop: '1.5em'}}>{props.children}</div>
        </div>
    )
};
