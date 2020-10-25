import React, { Component } from "react";

const Context = React.createContext();

class ContextProvider extends Component {

  render() {
    return (
      <Context.Provider value="value">
        {this.props.children}
      </Context.Provider>
    );
  }
}

const ContextConsumer = Context.Consumer;

export { ContextProvider, ContextConsumer };
