package src;

import java.util.Scanner;

public class GameManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 플레이어 생성
        System.out.print("[System] 캐릭터 이름: ");
        String name = scanner.nextLine();
        System.out.print("[System] 직업 (전사/마법사 등): ");
        String job = scanner.nextLine();
        Player player = new Player(name, job);

        // 몬스터 목록 생성
        Monster[] monsters = new Monster[] {
                new Monster("고블린", 50, 8,   5,  new Item("체력 포션", "HP +15", 15)),
                new Monster("오크",   80, 12,  10, new Item("중급 포션", "HP +30", 30)),
                new Monster("오우거", 120, 20, 20, new Item("고급 포션", "HP +50", 50))
        };

        // 몬스터별 공격 간격 (ms)
        long[] intervals = {7000, 5000, 3000};
        // 고블린 7초, 오크 5초, 오우거 3초

        // 게임 진행
        player.showStats(); // 플레이어 스탯 출력
        int monsterIndex = 0;

        while (player.isAlive() && monsterIndex < monsters.length) {
            Monster monster = monsters[monsterIndex];
            System.out.println("\n🔥 야생의 " + monster.getName() + "이(가) 나타났다!");

            // 자동 공격 스레드 시작
            MonsterAutoAttackThread monsterThread =
                    new MonsterAutoAttackThread(monster, player, intervals[monsterIndex]);
            monsterThread.start();

            // 싸움
            while (true) {
                // 플레이어가 살았나죽엇나 확인
                if (!player.isAlive()) {
                    System.out.println("\n[System] " + player.getName() + "이(가) 쓰러졌습니다. 게임 오버!");
                    monsterThread.stopThread();
                    break;
                }
                if (!monster.isAlive()) {
                    System.out.println("\n[System] " + monster.getName() + "을(를) 처치했습니다!");
                    player.gainExp(monster.getExpDrop());
                    monsterThread.stopThread();
                    break;
                }

                // 사용자 행동 입력
                System.out.println("┌─────────────────────────────┐");
                System.out.println("│  1) 공격   2) 방어          │");
                System.out.println("│  3) 아이템 4) 도망          │");
                System.out.println("└─────────────────────────────┘");
                System.out.print("[System] 행동을 선택하세요: ");
                String input = scanner.nextLine();
                System.out.println();

                monsterThread.updateLastActionTime();

                if (input.equals("1")) {
                    // 공격
                    player.attack(monster);

                } else if (input.equals("2")) {
                    // 방어
                    System.out.println("[System] " + player.getName() + "이(가) 방어 자세를 취합니다!");
                    monster.attack(player, true);

                } else if (input.equals("3")) {
                    // 아이템 사용
                    System.out.println("[System] " + player.getName()
                            + "이(가) 체력 포션을 사용합니다!");
                    player.heal(15);

                } else if (input.equals("4")) {
                    // 도망
                    System.out.println("[System] " + player.getName() + "이(가) 도망쳤습니다! 게임 오버!");
                    monsterThread.stopThread();
                    // 전체 게임 종료
                    monsterIndex = monsters.length;
                    break;

                } else {
                    // 잘못된 입력 예외처리
                    System.out.println("[System] 잘못된 입력입니다. 다시 선택하세요.\n");
                    continue;
                }

                // 행동 직후 체크
                if (!player.isAlive()) {
                    System.out.println("\n[System] " + player.getName() + "이(가) 쓰러졌습니다. 게임 오버!");
                    monsterThread.stopThread();
                    break;
                }
                if (!monster.isAlive()) {
                    System.out.println("\n[System] " + monster.getName() + "을(를) 처치했습니다!");
                    player.gainExp(monster.getExpDrop());
                    monsterThread.stopThread();
                    break;
                }
                if (input.equals("4")) {
                    // 도망
                    break;
                }
            }

            // 싸움 끝났고, 플레이어 살아있고, 몬스터가 죽었다면 다음 몬스터로
            if (player.isAlive() && !monster.isAlive()) {
                monsterIndex++;
            }
        }

        // 모든 몬스터 처치 여부 확인 & 종료 메시지
        if (monsterIndex >= monsters.length && player.isAlive()) {
            // 모든 몬스터 처치 성공
            System.out.println("\n[System] 모든 몬스터를 다 처치했습니다! 축하합니다!");
            System.out.println("[System] 몬스터가 없으므로 게임이 종료되었습니다. 수고하셨습니다!\n");
        } else {
            // 도망 or 플레이어 사망
            System.out.println("\n[System] 게임이 종료되었습니다. 수고하셨습니다!\n");
        }

        scanner.close();
    }
}
