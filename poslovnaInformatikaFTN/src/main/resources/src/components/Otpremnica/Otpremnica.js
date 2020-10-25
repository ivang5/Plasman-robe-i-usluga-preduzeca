import React, { Component } from "react";
import Podaci from "./Podaci";
import Tabela from "./Tabela";
import Footer from "./Footer";

export default class Otpremnica extends Component {
  render() {
    return (
      <div>
        <Podaci />
        <Tabela />
        <Footer />
      </div>
    );
  }
}
