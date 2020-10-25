import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class IzmenaModal extends Component {
  state = {
    naziv: "",
    pib: 0,
    telefon: "",
  };

  handleNazivChange = (e) => {
    this.setState({
      naziv: e.target.value,
    });
  };

  handlePibChange = (e) => {
    this.setState({
      pib: e.target.value,
    });
  };

  handleTelefonChange = (e) => {
    this.setState({
      telefon: e.target.value,
    });
  };

  render() {
    const { modalOpen, saveModal, closeModal } = this.props;
    const { naziv, pib, telefon } = this.props.preduzece;
    if (!modalOpen) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-8 mx-auto col-md-6 col-lg-4 text-center text-capitalize p-5"
              >
                <h3>Izmena preduzeća</h3>
                <hr />
                <br />
                <h5 className="mb-3">
                  naziv: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={naziv}
                    onChange={this.handleNazivChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  PIB: <br />{" "}
                  <input
                    type="number"
                    className="form-control"
                    defaultValue={pib}
                    onChange={this.handlePibChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  telefon: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={telefon}
                    onChange={this.handleTelefonChange}
                  ></input>
                </h5>
                <Link to="/informacije">
                  <button
                    className="btn btn-success m-4"
                    onClick={() =>
                      saveModal(
                        this.state.naziv,
                        this.state.pib,
                        this.state.telefon
                      )
                    }
                  >
                    Izmeni
                  </button>
                </Link>
                <Link to="/informacije">
                  <button className="btn btn-danger m-4" onClick={closeModal}>
                    Odustani
                  </button>
                </Link>
              </div>
            </div>
          </div>
        </ModalContainer>
      );
    }
  }
}

const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  #modal {
    background: var(--mainWhite);
  }
`;
