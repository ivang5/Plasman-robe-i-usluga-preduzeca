import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class AddRacunModal extends Component {
  state = {
    nazivBanke: "",
    brojRacuna: "",
  };

  handleNazivBankeChange = (e) => {
    this.setState({
      nazivBanke: e.target.value,
    });
  };

  handleBrojRacunaChange = (e) => {
    this.setState({
      brojRacuna: e.target.value,
    });
  };

  render() {
    const { modalAddRacun, addRacun, closeModal } = this.props;
    if (!modalAddRacun) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-8 mx-auto col-md-6 col-lg-6 text-center text-capitalize p-5"
              >
                <h3>Dodavanje računa</h3>
                <hr />
                <br />
                <div className="input-group form-group">
                  <label className="card-label mr-3 mt-1">Naziv banke:</label>
                  <input
                    type="text"
                    className="form-control"
                    onChange={this.handleNazivBankeChange}
                  />
                </div>
                <div className="input-group form-group">
                  <label className="card-label mr-3 mt-1">Broj racuna:</label>
                  <input
                    type="text"
                    className="form-control"
                    onChange={this.handleBrojRacunaChange}
                  />
                </div>
                <Link to="/informacije">
                  <button
                    className="btn btn-success m-4"
                    onClick={() =>
                      addRacun(this.state.nazivBanke, this.state.brojRacuna)
                    }
                  >
                    Dodaj
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
