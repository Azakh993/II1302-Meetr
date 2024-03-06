
# Meetr: Meeting Management for Inclusive Meetings

## Overview

Meetr is an innovative meeting management Android application developed to enhance inclusivity in in-person meetings by prioritizing less vocal participants. Utilizing light and proximity sensors on Android devices, Meetr interprets hand gestures as requests to speak, introducing a dynamic and interactive user experience. Key features include a user queuing system based on an inclusiveness algorithm, a reply function for direct communication with the current speaker, concurrent meeting capabilities, and a consensus request feature to facilitate group decision-making.

## Features

- **User Queuing System**: Employs an inclusiveness algorithm to ensure fair speaking opportunities.
- **Gesture Recognition**: Uses device sensors to interpret hand gestures as speaking requests.
- **Reply Function**: Allows users to directly respond to the current speaker.
- **Concurrent Meetings**: Supports the setup of multiple meetings simultaneously.
- **Group Consensus**: Facilitates collective decision-making by enabling participants to request a group consensus.

## Architecture

Meetr is built on a client-server architecture with the client represented by the Android application and the server powered by Firebase. This setup enables efficient data exchange and real-time updates. Key components include:

- **Client-Side**: Android application utilizing MVVM architecture for responsive UI and intuitive interaction.
- **Server-Side**: Firebase framework for data storage, user authentication, real-time messaging, and synchronization.

## Project Management

The development of Meetr adhered to the Scrum framework with adaptations to fit the project's needs. Key practices included pair programming, code review, and a designated note taker role to enhance collaboration and productivity.

## Challenges and Achievements

Throughout its development, Meetr faced and overcame several challenges, including issues with device sensor calibration and code management. Key achievements include the development of the unique user queuing system, the implementation of real-time gesture monitoring, and the ability to host concurrent meetings.

## Future Development

Future work on Meetr should focus on refining the user interface and user experience, further enhancing the inclusiveness algorithm, and expanding the application's adaptability to various meeting scenarios.
