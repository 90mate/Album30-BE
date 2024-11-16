---
name: Feature request
about: 구현할 기능에 대해 설명해주세요
title: ''
labels: ":sparkles: feature"
assignees: ''

---

name: "✨ Feature Request"
description: 새로운 기능 요청을 작성해주세요.
title: "[ Feature ] "
body:
  - type: markdown
    attributes:
      value: |
        작성 예시 : "[ Feature 1 ] 새로운 기능에 대한 간략한 설명"
  - type: textarea
    id: feat-description
    attributes:
      label: 🛠 구현할 기능
      description: 요청하는 기능에 대한 자세한 설명을 작성해주세요.
      placeholder: 설명을 작성해주세요.
    validations:
      required: true
  - type: textarea
    id: feat-list
    attributes:
      label: 📝 결과 예상
      description: 요청된 기능이 어떤 결과를 가져올지 작성해주세요.
      placeholder: 목록을 작성해주세요.
    validations:
      required: false
  - type: textarea
    id: reproduction
    attributes:
      label: 🔍 참고한 자료
      description: 기능을 구현할때 참고할 자료가 있다면 작성해주세요.
      placeholder: 설명을 작성해주세요.
    validations:
      required: false
