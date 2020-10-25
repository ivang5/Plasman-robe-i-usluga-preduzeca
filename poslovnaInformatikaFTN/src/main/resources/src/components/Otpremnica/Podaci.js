import React, { Component } from "react";
import styled from "styled-components";

export default class Podaci extends Component {
  render() {
    return (
      <DivWrapper className="container-fluid mt-5">
        <div className="row my-2">
          <div className="col-10 col-md-5">
            <h1>KETIN Z.Z.</h1>
            <h5>23243 BOTOŠ, Toze Markovića 6</h5>
            <h5>Tekući račun: 220-129698-65</h5>
            <br />
            <br />
            <h2>Račun/otpremnica 1/1</h2>
            <br />
            <br />
            <br />
            <p>Datum racuna</p>
            <p>Mesto izdavanja</p>
            <p>Datum prometa</p>
            <p>Mesto prometa</p>
            <p>Rok plaćanja</p>
            <p>Nacin plaćanja</p>
          </div>
          <div className="col-md-2 d-none d-lg-block"></div>
          <div className="col-8 col-md-5">
            <p>Telefon</p>
            <p>Matični broj</p>
            <p>Delatnost</p>
            <p>PIB</p>
            <br />
            <br />
            <br />
            <h1>PG DRAGAN KETIN</h1>
            <br />
            <br />
            <h1>23243 BOTOŠ</h1>
            <h2>Toze Markovića 6</h2>
            <br />
            <br />
            <h3>PIB: 801518001931</h3>
          </div>
        </div>
      </DivWrapper>
    );
  }
}

const DivWrapper = styled.div`
  margin-left: 4.5rem;
`;
