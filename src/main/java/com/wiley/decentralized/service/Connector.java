package com.wiley.decentralized.service;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Connector {

    private static Gateway gateway;


    public static Gateway connect() throws Exception {
        if (gateway == null) {
            // Load a file system based wallet for managing identities.
            Path walletPath = Paths.get("wallet");
            Wallet wallet = Wallets.newFileSystemWallet(walletPath);
            // load a CCP
            Path networkConfigPath = Paths.get("..", "..", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

            Gateway.Builder builder = Gateway.createBuilder();
            builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
            gateway = builder.connect();
            // return builder.connect();
        }
        return gateway;

    }
}
